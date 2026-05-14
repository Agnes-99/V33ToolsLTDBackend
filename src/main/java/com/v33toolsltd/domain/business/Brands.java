package com.v33toolsltd.domain.business;

public enum Brands {
    V33_TOOLS("V33 Tools");

    private final String displayName;
    private Brands(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}