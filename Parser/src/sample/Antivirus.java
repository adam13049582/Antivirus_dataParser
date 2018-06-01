package sample;

public final class Antivirus {
    private  String name;
    private  String version;
    private  String date;
    private  String firewall;
    private  String malware;
    private  String parental;
    private  String http;
    private  String ransomware;
    private  String backup;
    private  String browsing;
    private  String real;
    private  String anti;
    private  String password;
    private  String wifi;
    private  String data;



    public Antivirus(String name, String ver, String date, String fir, String mal,String par, String ht, String ran, String back, String brows, String rea, String ant, String pass, String wif, String dat) {
        super();
        this.name = new String(name);
        this.version = new String (ver);
        this.date = new String (date);
        this.firewall = new String (fir);
        this.malware = new String (mal);
        this.parental = new String (par);
        this.http = new String (ht);
        this.ransomware = new String (ran);
        this.backup = new String (back);
        this.browsing = new String (brows);
        this.real = new String (rea);
        this.anti = new String (ant);
        this.password = new String (pass);
        this.wifi = new String (wif);
        this.data = new String (dat);


    }


    public String getName() {
        return name;
    }

    public void setName(String fname) {
        this.name=fname;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String fversion) {
        this.version=fversion;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String fdate) {
        this.date=fdate;
    }
    public String getFirewall() {
        return firewall;
    }

    public void setFirewall(String ffirewall) {
        this.firewall=ffirewall;
    }

    public String getMalware() {
        return malware;
    }

    public void setMalware(String fmalware) {
        this.malware=fmalware;
    }

  public String getParental() {
        return parental;
    }

    public void setParental(String fparental) {
        this.parental=fparental;
    }
    public String getHttp() {
        return http;
    }

    public void setHttp(String fhttp) {
        this.http=fhttp;
    }

    public String getRansomware() {
        return ransomware;
    }

    public void setRansomware(String fransomware) {
        this.ransomware=fransomware;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String fbackup) {
        this.backup=fbackup;
    }
    public String getBrowser() {
        return browsing;
    }

    public void setBrowser(String fbrowser) {
        this.browsing=fbrowser;
    }

    public String getReal() {
        return real;
    }

    public void setReal(String freal) {
        this.real=freal;
    }

    public String getAnti() {
        return anti;
    }

    public void setAnti(String fanti) {
        this.anti=fanti;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String fpassword) {
        this.password=fpassword;
    }
    public String getWifi() {
        return wifi;
    }

    public void setWifi(String fwifi) {
        this.wifi=fwifi;
    }
    public String getData() {
        return data;
    }

    public void setData(String fdata) {
        this.data=fdata;
    }
}

