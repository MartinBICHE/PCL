package eu.telecomnancy.pcl.serpython.types;

public abstract class Type implements CompatibleType {
    public static class Integer extends Type {
        public Integer() {

        }

        public boolean isCompatibleWith(Type type) {
            return type instanceof Integer;
        }
    }

    public static class String extends Type {
        public String() {

        }

        public boolean isCompatibleWith(Type type) {
            return type instanceof String;
        }
    }

    public static class Bool extends Type {
        public Bool() {

        }

        public boolean isCompatibleWith(Type type) {
            return type instanceof Bool;
        }
    }

    public static abstract class Array extends Type {
        
    }

    public static class AnyArray extends Array {
        public AnyArray() {
            
        }

        public boolean isCompatibleWith(Type type) {
            return type instanceof AnyArray;
        }
    }

    public static class ConcreteArray extends Array {
        private int size;

        public ConcreteArray(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public boolean isCompatibleWith(Type type) {
            if(type instanceof ConcreteArray) {
                return ((ConcreteArray) type).getSize() == this.size;
            } else if(type instanceof AnyArray) {
                return true;
            }
            return false;
        }
    }

    public static abstract class Function extends Type {

    }

    public static class ConcreteFunction extends Function {
        private Type returnType;
        private Type[] args;

        public ConcreteFunction(Type returnType, Type[] args) {
            this.returnType = returnType;
            this.args = args;
        }

        public ConcreteFunction(int arity) {
            this(null, new Type[arity]);
        }

        public Type getReturnType() {
            return returnType;
        }

        public Type[] getArgs() {
            return args;
        }

        public boolean isCompatibleWith(Type type) {
            return false;
        }
    }
}
