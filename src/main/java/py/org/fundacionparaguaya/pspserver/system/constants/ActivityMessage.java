package py.org.fundacionparaguaya.pspserver.system.constants;

/**
 * Created by bsandoval on 21/05/18.
 */
public enum ActivityMessageKey {
    HOUSEHOLD_FIRST_SNAPSHOT("activity.householdFirstSnapshot"),
    ADMIN_SNAPSHOTS("activity.adminSnapshots"),
    HUB_SNAPSHOTS("activity.hubSnapshots");

    private final String value;

    ActivityMessageKey(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
