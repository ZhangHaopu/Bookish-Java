package org.softwire.training.bookish.services;


import org.softwire.training.bookish.models.database.CopyWithLoanInfo;
import org.softwire.training.bookish.models.database.Technology;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckService extends DatabaseService{
    public void checkoutBycopyid(String username, int copyid){
        jdbi.useHandle(handle ->{
                handle.createUpdate("UPDATE copiesOfBook SET available = FALSE WHERE id_copyofbooks = :id" )
                        .bind("id", copyid)
                        .execute();
                handle.createUpdate("INSERT INTO transactions (userid, bookid, outdate, duedate) " +
                        "SELECT members.id_members, :bookid, NOW(), DATE_ADD(NOW(), INTERVAL 14 DAY) " +
                        "FROM members WHERE members.name = :username")
                        .bind("username" , username)
                        .bind("bookid", copyid)
                        .execute();

                }
        );
    }

    public LocalDateTime checkinBycopyid(int copyid){
        return jdbi.withHandle(handle ->{
            handle.createUpdate("UPDATE copiesOfBook SET available = TRUE WHERE id_copyofbooks = :id")
                    .bind("id", copyid)
                    .execute();
            List<LocalDateTime> duedate = handle.createQuery("SELECT dueDate From transactions WHERE bookid = :id AND returnDate IS NULL")
                    .bind("id", copyid)
                    .mapTo(LocalDateTime.class)
                    .list();
            handle.createUpdate("UPDATE transactions SET returnDate = NOW() WHERE bookid = :id")
                    .bind("id", copyid)
                    .execute();
            return duedate.get(0);
        });

    }

    public List<CopyWithLoanInfo> getLoanedCopies() {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT copiesOfBook.id_copyofbooks AS copy_id, books.title AS title, books.author AS author, " +
                    "members.name AS member_name, transactions.outDate AS out_date, transactions.dueDate AS due_date " +
                    "FROM copiesOfBook " +
                    "LEFT JOIN transactions ON copiesOfBook.id_copyofbooks = transactions.bookid AND transactions.returnDate IS NULL " +
                    "LEFT JOIN books ON books.id_books = copiesOfBook.id " +
                    "LEFT JOIN members ON members.id_members = transactions.userid " +
                    "WHERE copiesOfBook.deletedDate IS NULL " +
                    "AND available = 0")
                    .mapToBean(CopyWithLoanInfo.class)
                    .list()
        );
    }



}
