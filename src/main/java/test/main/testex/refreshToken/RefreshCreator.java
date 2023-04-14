package test.main.testex.refreshToken;

import test.main.testex.entity.RefreshToken;
import test.main.testex.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import test.main.testex.repositories.RefreshRepository;
import test.main.testex.repositories.UserRepository;

import java.util.Date;
import java.util.UUID;

@Component
public class RefreshCreator {

    @Value(value="${jwt.refresh.expired}")
    private long expiredDate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshRepository refreshRepository;

    public RefreshToken createRef() {
        String refreshToken = UUID.randomUUID().toString();
        Date startDate = new Date();
        Date finishDate = new Date(startDate.getTime() + expiredDate);
        RefreshToken refreshElement = new RefreshToken(startDate, finishDate, refreshToken);
        refreshRepository.save(refreshElement);
        return refreshElement;
    }

    public void updateRef(User user){
        RefreshToken refreshToken = user.getRefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        Date startDate = new Date();
        refreshToken.setStartDate(new Date());
        refreshToken.setExpiredDate(new Date(startDate.getTime() + expiredDate));
        refreshRepository.save(refreshToken);
    }

}
