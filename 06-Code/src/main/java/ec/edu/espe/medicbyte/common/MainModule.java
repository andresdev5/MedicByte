package ec.edu.espe.medicbyte.common;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ec.edu.espe.medicbyte.common.core.DatabaseManager;
import ec.edu.espe.medicbyte.common.core.DatabaseManager.DatabaseContext;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.service.impl.AppointmentService;
import ec.edu.espe.medicbyte.service.impl.AuthService;
import ec.edu.espe.medicbyte.service.impl.MedicService;
import ec.edu.espe.medicbyte.service.impl.PatientService;
import ec.edu.espe.medicbyte.service.impl.RoleService;
import ec.edu.espe.medicbyte.service.impl.SpecialityService;
import ec.edu.espe.medicbyte.service.impl.UserService;
import ec.edu.espe.medicbyte.service.impl.LocationService;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IMedicService;
import ec.edu.espe.medicbyte.service.IAppointmentService;
import ec.edu.espe.medicbyte.service.ILocationService;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.service.IRoleService;
import ec.edu.espe.medicbyte.service.ISpecialityService;
import ec.edu.espe.medicbyte.service.IUserService;

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
        // bind database manager
        DatabaseContext dbcontext = new DatabaseManager.DatabaseContext(
            "ec.edu.espe.medicbyte.model",
            "medicbyte",
            "localhost",
            27017
        );
        
        this.bind(DatabaseManager.class).toInstance(new DatabaseManager(dbcontext));
        
        // bind windows manager
        this.bind(WindowsManager.class).in(Singleton.class);
        
        // bind the controller router
        this.bind(Router.class).in(Singleton.class);
        
        // bind services
        this.bind(IAuthService.class)
            .to(AuthService.class).in(Singleton.class);
        this.bind(IUserService.class)
            .to(UserService.class).in(Singleton.class);
        this.bind(IMedicService.class)
            .to(MedicService.class).in(Singleton.class);
        this.bind(IPatientService.class)
            .to(PatientService.class).in(Singleton.class);
        this.bind(IAppointmentService.class)
            .to(AppointmentService.class).in(Singleton.class);
        this.bind(IRoleService.class)
            .to(RoleService.class).in(Singleton.class);
        this.bind(ISpecialityService.class)
            .to(SpecialityService.class).in(Singleton.class);
        this.bind(ILocationService.class)
            .to(LocationService.class).in(Singleton.class);
    }
}
