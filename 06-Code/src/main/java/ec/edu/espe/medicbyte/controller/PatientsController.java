package ec.edu.espe.medicbyte.controller;

import com.google.inject.Inject;
import ec.edu.espe.medicbyte.common.core.Controller;
import ec.edu.espe.medicbyte.common.core.Router;
import ec.edu.espe.medicbyte.common.core.WindowsManager;
import ec.edu.espe.medicbyte.model.Appointment;
import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.service.IAppointmentService;
import ec.edu.espe.medicbyte.service.IAuthService;
import ec.edu.espe.medicbyte.service.IPatientService;
import ec.edu.espe.medicbyte.view.FrmPatients;
import ec.edu.espe.medicbyte.view.MainWindow;
import ec.edu.espe.medicbyte.view.PatientListItem;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Junior Jurado
 */
public class PatientsController extends Controller {
    public class ChartData {
        private String serie;
        private String date;
        private Number count;

        public ChartData(String serie, String date, Number count) {
            this.serie = serie;
            this.date = date;
            this.count = count;
        }

        public String getSerie() {
            return serie;
        }

        public void setSerie(String serie) {
            this.serie = serie;
        }

        public Number getCount() {
            return count;
        }

        public void setCount(Number count) {
            this.count = count;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
    
    @Inject() private WindowsManager windowsManager;
    @Inject() private Router router;
    @Inject() private IAuthService authService;
    @Inject() private IPatientService patientService;
    @Inject() private IAppointmentService appointmentService;
    
    InputStream reportInput;
    JasperDesign reportDesign;
    JasperReport report;
    
    public PatientsController() {
        try {
            reportInput = getClass().getResourceAsStream("/reports/patient.jrxml");
            reportDesign = JRXmlLoader.load(reportInput);
            report = JasperCompileManager.compileReport(reportDesign);
        } catch (Exception exception) {
            Logger.getLogger(PatientsController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
    
    @Override
    public void init() {
        MainWindow mainWindow = windowsManager.getAs(MainWindow.class);
        FrmPatients view = new FrmPatients();
        User user = authService.getCurrentUser();
        
        view.set("currentUser", authService.getCurrentUser());
        view.set("patients", patientService.getAll());
        view.listen("requestPatientReport", (args) -> generatePatientReport(args.get(0)));
        mainWindow.display(view);
    }
    
    public void generatePatientReport(Patient patient) {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("patient", patient);
            parameters.put("profile", patient.getProfile());

            BufferedImage patientPhoto;

            if (patient.getProfile().getAvatar() != null) {
                patientPhoto = ImageIO.read(new ByteArrayInputStream(patient.getProfile().getAvatar()));
            } else {
                Image userIcon = IconFontSwing.buildImage(FontAwesome.USER, 96, new Color(90, 90, 90));
                patientPhoto = new BufferedImage(
                    userIcon.getWidth(null), userIcon.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = patientPhoto.createGraphics();
                graphics.drawImage(userIcon, 0, 0, null);
                graphics.dispose();
            }

            parameters.put("patientPhoto", patientPhoto);
            
            List<ChartData> data = new ArrayList<>();            
            Map<String, Integer> appointmentsPerMonth = new HashMap<>();
            
            List<Appointment> appointments = appointmentService.getAll()
                .stream()
                .filter(appointment -> appointment.getPatient().getId() == patient.getId())
                .collect(Collectors.toList());
            
            appointments.forEach(appointment -> {
                String date = appointment.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                appointmentsPerMonth.put(date, appointmentsPerMonth.getOrDefault(date, 0) + 1);
            });
            
            appointmentsPerMonth.forEach((date, count) -> {
                data.add(new ChartData("Appointments", date, count));
            });
            
            data.sort((ChartData a, ChartData b) -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date1 = LocalDate.parse(a.getDate(), formatter);
                LocalDate date2 = LocalDate.parse(b.getDate(), formatter);
                
                return date1.compareTo(date2);
            });
            
            JRBeanCollectionDataSource bean = new JRBeanCollectionDataSource(data);
            parameters.put("CHART_DATASET", bean);
            
            
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setTitle("Patient report");
            viewer.setVisible(true);
        } catch (JRException | IOException ex) {
            Logger.getLogger(PatientListItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
