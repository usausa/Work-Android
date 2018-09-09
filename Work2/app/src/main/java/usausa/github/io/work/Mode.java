package usausa.github.io.work;

public enum Mode {

    MODE1() {
        @Override
        public boolean isEnableAction1() {
            return true;
        }

        @Override
        public boolean isEnableAction2() {
            return true;
        }
    },

    MODE2() {
        @Override
        public boolean isEnableAction1() {
            return false;
        }

        @Override
        public boolean isEnableAction2() {
            return true;
        }
    },

    MODE3() {
        @Override
        public boolean isEnableAction1() {
            return true;
        }

        @Override
        public boolean isEnableAction2() {
            return false;
        }
    };

    public abstract boolean isEnableAction1();

    public abstract boolean isEnableAction2();
}
