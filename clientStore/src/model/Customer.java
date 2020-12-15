package model;
import java.io.Serializable;
import java.util.Objects;

public class Customer  implements Serializable{
    private int idCustomer;
    private String addr;
    private String full_name;
    private String phone;
    private String settlement_account;
    private String BIK;
    private String KPP;
    private String INN;
    private int status;

    @Override
    public String toString() {
        return full_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return idCustomer == customer.idCustomer &&
                status == customer.status &&
                Objects.equals(addr, customer.addr) &&
                Objects.equals(full_name, customer.full_name) &&
                Objects.equals(phone, customer.phone) &&
                Objects.equals(settlement_account, customer.settlement_account) &&
                Objects.equals(BIK, customer.BIK) &&
                Objects.equals(KPP, customer.KPP) &&
                Objects.equals(INN, customer.INN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCustomer, addr, full_name, phone, settlement_account, BIK, KPP, INN, status);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSettlement_account() {
        return settlement_account;
    }

    public void setSettlement_account(String settlement_account) {
        this.settlement_account = settlement_account;
    }

    public String getBIK() {
        return BIK;
    }

    public void setBIK(String BIK) {
        this.BIK = BIK;
    }

    public String getKPP() {
        return KPP;
    }

    public void setKPP(String KPP) {
        this.KPP = KPP;
    }

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

}
