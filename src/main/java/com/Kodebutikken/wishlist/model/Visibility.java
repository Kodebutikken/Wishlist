    package com.Kodebutikken.wishlist.model;

    public enum Visibility {
        PUBLIC("Offentlig"),
        PRIVATE("Privat");

        private String displayName;

        Visibility(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
