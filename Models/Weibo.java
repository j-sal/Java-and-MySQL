package Models;

public class Weibo {

    private int sinaUsers = 0;
    private int nonSina = 0;
    private int total = 0;

    public void setSinaUsers(int sinaUsers) {
        this.sinaUsers = sinaUsers;
    }

    public int getSinaUsers() {
        return sinaUsers;
    }

    public void setNonSina(int nonSina) {
        this.nonSina = nonSina;
    }

    public int getNonSina() {
        return nonSina;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

}
