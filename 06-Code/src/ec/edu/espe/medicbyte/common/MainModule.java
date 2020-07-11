package ec.edu.espe.medicbyte.common;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ec.edu.espe.medicbyte.common.core.Console;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.service.AppointmentService;
import ec.edu.espe.medicbyte.service.AuthService;
import ec.edu.espe.medicbyte.service.MedicService;
import ec.edu.espe.medicbyte.service.PatientService;
import ec.edu.espe.medicbyte.service.RoleService;
import ec.edu.espe.medicbyte.service.SpecialityService;
import ec.edu.espe.medicbyte.service.UserService;
import ec.edu.espe.medicbyte.service.impl.AppointmentServiceImpl;
import ec.edu.espe.medicbyte.service.impl.AuthServiceImpl;
import ec.edu.espe.medicbyte.service.impl.MedicServiceImpl;
import ec.edu.espe.medicbyte.service.impl.PatientServiceImpl;
import ec.edu.espe.medicbyte.service.impl.RoleServiceImpl;
import ec.edu.espe.medicbyte.service.impl.SpecialityServiceImpl;
import ec.edu.espe.medicbyte.service.impl.UserServiceImpl;

/**
 *
 * @author Andres Jonathan J.
 */
public class MainModule extends AbstractModule {
    private Application application;
    
    public void setApplication(Application application) {
        this.application = application;
    }
    
    @Provides
    Application provideApplication() {
        return application;
    }
    
    @Override
    protected void configure() {
        // bind the controller router
        this.bind(Router.class).in(Singleton.class);
        
        // bind services
        this.bind(AuthService.class)
            .to(AuthServiceImpl.class).in(Singleton.class);
        this.bind(UserService.class)
            .to(UserServiceImpl.class).in(Singleton.class);
        this.bind(MedicService.class)
            .to(MedicServiceImpl.class).in(Singleton.class);
        this.bind(PatientService.class)
            .to(PatientServiceImpl.class).in(Singleton.class);
        this.bind(AppointmentService.class)
            .to(AppointmentServiceImpl.class).in(Singleton.class);
        this.bind(RoleService.class)
            .to(RoleServiceImpl.class).in(Singleton.class);
        this.bind(SpecialityService.class)
            .to(SpecialityServiceImpl.class).in(Singleton.class);
        
        // bind console singleton instance
        this.bind(Console.class).toInstance(Console.getInstance());
    }
}
