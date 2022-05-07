package study.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.jdbc.domain.Member;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void save() throws SQLException {
        // save
        Member member = new Member("member", 10000);
        repository.save(member);
    }

    @Test
    void select() throws SQLException{
        // findById
        Member member = new Member("member", 10000);
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMemer {}", member == findMember); // false
        log.info("member equals findMember {}", member.equals(findMember)); // true
        assertThat(findMember).isEqualTo(member);
    }
    @Test
    void update() throws SQLException{
        // update : money : 10000 -> 20000
        Member member = new Member("member", 10000);
        repository.update(member.getMemberId(), 20000);
        Member updateMember = repository.findById(member.getMemberId());
//        assertThat(updateMember).isEqualTo(member);
        assertThat(updateMember.getMoney()).isEqualTo(20000);
    }

    @Test
    void delete() throws SQLException {
        Member member = new Member("member", 10000);
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
        //Member deletedMember = repository.findById(member.getMemberId());

    }
}