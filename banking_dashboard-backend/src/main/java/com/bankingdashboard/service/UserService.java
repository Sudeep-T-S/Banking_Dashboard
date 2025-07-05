package com.barclaysbanking.service;

import com.barclaysbanking.dto.*;
import com.barclaysbanking.entity.*;

public interface UserService {
    User registerUser(UserRegistrationRequest request);
    User loginUser(UserLoginRequest request);
    User getUserByEmail(String email);
    DashboardResponse getDashboard(String email);
}
