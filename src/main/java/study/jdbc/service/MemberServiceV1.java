package study.jdbc.service;

import lombok.RequiredArgsConstructor;
import study.jdbc.domain.Member;
import study.jdbc.repository.MemberRepositoryV1;

import java.sql.SQLException;

@RequiredArgsConstructor  // fianl 로 생성자를 만든다
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;  // @RequiredArgsConstructor

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
