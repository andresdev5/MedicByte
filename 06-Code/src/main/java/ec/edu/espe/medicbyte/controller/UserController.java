package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Routed;
import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.model.UserProfile;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IUserService;
import ec.edu.espe.medicbyte.view.FrmEditProfile;
import ec.edu.espe.medicbyte.view.FrmEditProfile.UserProfileContext;
import ec.edu.espe.medicbyte.view.MainWindow;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres Jonathan J.
 */
public class UserController extends Controller {
    @Inject() private WindowsManager windowsManager;
    @Inject() private IAuthService authService;
    @Inject() private IUserService userService;
    private final AtomicBoolean savingProfile = new AtomicBoolean(false);
    
    @Override public void init() {}
    
    @Routed("editProfile")
    public void editProfile() {
        MainWindow window = windowsManager.getAs(MainWindow.class);
        View view = new FrmEditProfile();
        UserProfileContext context = new UserProfileContext();
        
        view.listen("submit", (args) -> {
            UserProfileContext saved = args.get(0);
            User user = authService.getCurrentUser();
            UserProfile profile = user.getProfile();
            
            if (savingProfile.get()) {
                return;
            }
            
            savingProfile.set(true);
            
            if (saved.fullName != null && !saved.fullName.trim().isEmpty()) {
                profile.setFullName(saved.fullName);
            }
            
            if (saved.phone != null && !saved.phone.isEmpty()) {
                profile.setPhone(saved.phone);
            }
            
            if (saved.gender != null) {
                profile.setGender(saved.gender);
            }
            
            if (saved.birthDate != null) {
                profile.setBirthday(saved.birthDate);
            }
            
            if (saved.avatar != null) {
                try {
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    ImageIO.write(saved.avatar, "png", output);
                    profile.setAvatar(output.toByteArray());
                } catch (IOException exception) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
            
            // TODO: fix this
            //UserProfile updatedProfile = userService.updateUserProfile(user.getId(), profile);
            //user.setProfile(updatedProfile);
            savingProfile.set(false);
            view.emit("updatedProfile");
            window.set("userContext", user);
        });
        
        UserProfile profile = authService.getCurrentUser().getProfile();
        
        //context.avatar = authService.getCurrentUser().getAvatar();
        context.fullName = profile.getFullName();
        context.birthDate = profile.getBirthday();
        context.gender = profile.getGender();
        context.phone = profile.getPhone();
        
        if (profile.getAvatar() != null) {
            try {
                context.avatar = ImageIO.read(new ByteArrayInputStream(profile.getAvatar()));
            } catch (IOException exception) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
        
        view.set("userProfileContext", context);
        window.display(view);
    }
}
