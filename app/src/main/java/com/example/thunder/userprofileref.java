package com.example.thunder;

public class userprofileref {
    String usrproid;
    String usrproname;
    String usrproaddr;
    String usrproemail;
    String usrpropincd;
    String usrprophno;
    String usrpicurl;
    String description;
    String status;

    public userprofileref(){

    }

    public userprofileref(String usrproid,String usrproname, String usrproaddr, String usrproemail, String usrpropincd, String usrprophno, String usrpicurl, String description ,String status) {
        this.usrproid= usrproid;
        this.usrproname = usrproname;
        this.usrproaddr = usrproaddr;
        this.usrproemail = usrproemail;
        this.usrpropincd = usrpropincd;
        this.usrprophno = usrprophno;
        this.usrpicurl=usrpicurl;
        this.description=description;
        this.status=status;
    }
    public String getUsrproid() {
        return usrproid;
    }


    public String getUsrproname() {
        return usrproname;
    }

    public String getUsrproaddr() {
        return usrproaddr;
    }

    public String getUsrproemail() {
        return usrproemail;
    }

    public String getUsrpropincd() {
        return usrpropincd;
    }

    public String getUsrprophno() {
        return usrprophno;
    }

    public String getUsrpicurl() {
        return usrpicurl;
    }

    public String getDescription() {return description;}

    public String getStatus (){
        return status;
    }
}

