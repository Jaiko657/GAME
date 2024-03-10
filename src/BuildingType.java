public enum BuildingType {
    WORM_BREEDER, TOILET;

    private static final String[] names = {"Worm Breeder", "Toilet"};

    @Override
    public String toString() {
        return names[this.ordinal()];
    }
}
