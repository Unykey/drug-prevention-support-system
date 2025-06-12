package com.swp08.dpss.enums;

public enum User_Status {
    //Valid / Active States
    PENDING, //The user has signed up but has not completed verification
    VERIFIED, //Email or identity has been confirmed

    //Restricted / Limited Access States
    SUSPENDED, //Temporarily disabled due to policy violations, billing issues, etc.
    EXPIRED, //The account has not been active for long period
    LOCKED, //Locked out due to failed login attempts or security issues.

    //Invalid / Inactive States
    BANNED, //Permanently blocked due to serious violations or misuse.
    DELETED; //The account has been permanently removed or marked for deletion.

    public boolean isVerified() {
        return this.equals(VERIFIED);
    }

    public boolean isPending() {
        return this.equals(PENDING);
    }

    public boolean isTemporarilyRestricted() {
        return this.equals(SUSPENDED) || this.equals(EXPIRED) || this.equals(LOCKED);
    }

    public boolean isBanned() {
        return this.equals(BANNED);
    }

    public boolean isDeleted() {
        return this.equals(DELETED);
    }
}
