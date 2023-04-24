package com.geulkkoli.domain.user;

import com.geulkkoli.web.user.edit.EditFormDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;


@Transactional
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    // User 정보 업데이트 (이메일은 변경 불가 / 비밀번호는 따로 변경 처리)
    @Override
    public void update(Long id, EditFormDto form) {
        QUser user = QUser.user;
        queryFactory.update(user)
                .set(user.userName, form.getUserName())
                .set(user.nickName, form.getNickName())
                .set(user.phoneNo, form.getPhoneNo())
                .set(user.gender, form.getGender())
                .where(user.userId.eq(id))
                .execute();
    }

    @Override
    public void updatePassword(Long id, String password) {
        QUser user = QUser.user;
        queryFactory.update(user)
                .set(user.password, password)
                .where(user.userId.eq(id))
                .execute();
    }
}