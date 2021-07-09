package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Member;

import java.util.List;

public class MembersService extends DatabaseService {
    public List<Member> getMembers(){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id_members AS id, members.name AS name FROM members")
                        .mapToBean(Member.class)
                        .list()
        );
    }

    public void addMember(String name){
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO members (name) VALUE (:name)")
                    .bind("name", name)
                    .execute()
        );
    }

    public void editMember(int id, String name){
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE members SET name = :name WHERE id_members = :id")
                        .bind("name", name)
                        .bind("id",id)
                        .execute()
        );
    }
}
