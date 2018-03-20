package har.team.mapenv;

/**
 * Created by Invix on 3/10/2018.
 */
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Ward {
    private String binId;
    private String binNo;
  //  private String wardNo;

    //private String inCharge;

    public Ward() {
        //this constructor is required
    }

    public Ward(String binId, String binNo) {
        this.binId = binId;
        this.binNo = binNo;
      //  this.wardNo = wardNo;
        //this.inCharge = inCharge;
    }

    public String getBinId() {
        return binId;
    }

    public String getBinNo() {
        return binNo;
    }
    /*public String getWardNo() {
      //  return wardNo;
    }

    public String getInCharge() {
        return inCharge;
    }*/}
