package usausa.github.io.work.service.transfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Looper;

import com.annimon.stream.Stream;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class TransferService implements WifiP2pManager.ChannelListener, WifiP2pManager.PeerListListener, WifiP2pManager.ConnectionInfoListener {

    private final Context context;

    private final WiFiDirectReceiver receiver = new WiFiDirectReceiver();

    private final IntentFilter intentFilter = new IntentFilter();

    private final WifiP2pManager manager;

    private WifiP2pManager.Channel channel;

    private boolean enabled;

    private final PublishSubject<DeviceInformation> thisDeviceObservable = PublishSubject.create();

    private final PublishSubject<DeviceInformation[]> peerDeviceObservable = PublishSubject.create();

    public Observable<DeviceInformation> getThisDeviceObservable() {
        return thisDeviceObservable;
    }

    public Observable<DeviceInformation[]> getPeerDeviceObservable() {
        return peerDeviceObservable;
    }

    public TransferService(final Context context) {
        this.context = context;

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        manager = (WifiP2pManager)context.getSystemService(Context.WIFI_P2P_SERVICE);
    }

    public void start() {
        if (enabled) {
            return;
        }

        // [MEMO] 本当はstart/stopの都度initialize/closeすべきかもしれないが、SDKバージョンによっては対応していないので一度だけ行う形で
        if (channel == null) {
            channel = manager.initialize(context, Looper.getMainLooper(), this);
        }

        context.registerReceiver(receiver, intentFilter);

        enabled = true;
    }

    public void stop() {
        if (!enabled) {
            return;
        }

        context.unregisterReceiver(receiver);

        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Timber.d("********** success");
            }

            @Override
            public void onFailure(int reason) {
                Timber.d("********** removeGroup failure : %d", reason);
            }
        });

        // [MEMO] 本当はstart/stopの都度initialize/closeすべきかもしれないが、SDKバージョンによっては対応していないので一度だけ行う形で
        //channel.close();
        //channel = null;

        enabled = false;
    }

    public void startDiscover() {
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Timber.d("********** discoverPeers success");
            }

            @Override
            public void onFailure(int reason) {
                Timber.d("********** discoverPeers failure : %d", reason);
            }
        });
    }

    public void stopDiscover() {
        manager.stopPeerDiscovery(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Timber.d("********** stopPeerDiscovery success");
            }

            @Override
            public void onFailure(int reason) {
                Timber.d("********** stopPeerDiscovery failure : %d", reason);
            }
        });
    }

    public void connect(final String address) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = address;
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Timber.d("********** connect success");
            }

            @Override
            public void onFailure(int reason) {
                Timber.d("********** connect failure : %d", reason);
            }
        });
    }

    //--------------------------------------------------------------------------------
    // ChannelListener
    //--------------------------------------------------------------------------------

    @Override
    public void onChannelDisconnected() {
        Timber.d("********** onChannelDisconnected");

        // そもそもなにをした時に発生するか？、WiFiオフでは発生しない、pause/resumeも関係なし
        // [MEMO] 本当はstart/stopの都度initialize/closeすべきかもしれないが、SDKバージョンによっては対応していないので一度だけ行う形で
        if (enabled) {
            channel = manager.initialize(context, Looper.getMainLooper(), this);
        }
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        Timber.d("********** onPeersAvailable : size=[%d]", peers.getDeviceList().size());
        for (WifiP2pDevice device : peers.getDeviceList()){
            Timber.d("********** onPeersAvailable : device=[%s]", device.toString());
        }

        // 一覧の通知、stopDiscover()した時に0件で発生することもある(必ずではない)
        peerDeviceObservable.onNext(Stream.of(peers.getDeviceList()).map(x -> new DeviceInformation(x.deviceName, x.deviceAddress, x.status)).toArray(DeviceInformation[]::new));
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        Timber.d("********** onConnectionInfoAvailable : info=[%s]", info.toString());
    }

    private class WiFiDirectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                // [MEMO] WiFiオン、オフで発生、pause/resumeは関係なし、通知し、非接続状態の時は処理を呼ばないようにする？
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                Timber.d("********** EXTRA_WIFI_STATE : state=[%d]", state);
            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
                // [MEMO] discoverで発見時に発生、続けてrequestPeersを呼び出す
                Timber.d("********** WIFI_P2P_PEERS_CHANGED_ACTION");
                Objects.requireNonNull(manager).requestPeers(channel, TransferService.this);
            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                // TODO 被接続側の発生がどうなるかを確認！
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                Timber.d("********** WIFI_P2P_CONNECTION_CHANGED_ACTION : state=[%s]", networkInfo.getState());

                if (networkInfo.isConnected()) {
                    Objects.requireNonNull(manager).requestConnectionInfo(channel, TransferService.this);
                }
                // TODO ?
            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
                // [MEMO] Receiver登録時に発生、自分の情報を通知するだけ
                WifiP2pDevice device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
                Timber.d("********** WIFI_P2P_THIS_DEVICE_CHANGED_ACTION : name=[%s], address=[%s], type=[%s], type2=[%s], status=[%d]",
                        device.deviceName, device.deviceAddress, device.primaryDeviceType, device.secondaryDeviceType, device.status);
                thisDeviceObservable.onNext(new DeviceInformation(device.deviceName, device.deviceAddress, device.status));
            }
        }
    }
}
