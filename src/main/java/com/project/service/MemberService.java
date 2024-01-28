package com.project.service;

import com.project.model.Member;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface MemberService {
    public Member create(Member image);
    public List<Member> viewAll();
    public Member viewById(long id) throws SQLException;

    Member oneById(long id);
}
