package org.softwire.training.bookish.models.database;

import java.time.LocalDateTime;

public class CopyWithLoanInfo {
    int copy_id;
    boolean available;
    String member_name;
    LocalDateTime due_date;
    LocalDateTime out_date;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public LocalDateTime getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDateTime due_date) {
        this.due_date = due_date;
    }

    public LocalDateTime getOut_date() {
        return out_date;
    }

    public void setOut_date(LocalDateTime out_date) {
        this.out_date = out_date;
    }

    public int getCopy_id() {
        return copy_id;
    }

    public void setCopy_id(int copy_id) {
        this.copy_id = copy_id;
    }
}
