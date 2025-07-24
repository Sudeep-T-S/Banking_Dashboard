package com.bankingdashboard.service;

import com.bankingdashboard.dto.*;
import com.bankingdashboard.entity.*;

public interface UserService {
    User registerUser(UserRegistrationRequest request);
    User loginUser(UserLoginRequest request);
    User getUserByEmail(String email);
    DashboardResponse getDashboard(String email);
}
