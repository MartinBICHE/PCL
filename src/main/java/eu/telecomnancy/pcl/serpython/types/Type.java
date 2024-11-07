package eu.telecomnancy.pcl.serpython.types;

public abstract class Type {
    @Override
    public abstract boolean equals(Object obj);

    public static class Integer extends Type {
        public Integer() {

        }

        @Override
        public boolean equals(Object obj) {
            return switch(obj) {
                case Type.Integer i -> true;
                default -> false;
            };
        }
    }

    public static class String extends Type {
        public String() {

        }

        @Override
        public boolean equals(Object obj) {
            return switch(obj) {
                case Type.String s -> true;
                default -> false;
            };
        }
    }

    public static class Bool extends Type {
        public Bool() {

        }

        @Override
        public boolean equals(Object obj) {
            return switch(obj) {
                case Type.Bool b -> true;
                default -> false;
            };
        }
    }

    public static class Array extends Type {
        public Array() {

        }

        @Override
        public boolean equals(Object obj) {
            return switch(obj) {
                case Type.Array a -> true;
                default -> false;
            };
        }
    }

    public static class Function extends Type {
        public Function() {

        }

        @Override
        public boolean equals(Object obj) {
            return switch(obj) {
                case Type.Function f -> true;
                default -> false;
            };
        }
    }
}
