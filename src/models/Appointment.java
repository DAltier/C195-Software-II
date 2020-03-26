/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database;

/**
 *
 * @author Dana Altier
 */

public class Appointment {
    
    private final SimpleIntegerProperty appointmentId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty customerId = new SimpleIntegerProperty();
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleStringProperty contact = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    private final SimpleStringProperty start = new SimpleStringProperty();
    private final SimpleStringProperty end = new SimpleStringProperty();
    
    public Appointment() {
        
    }
    
    public Appointment(int id, int cId, String start, String end, String title, String description, String location, String contact) {
        
       setAppointmentId(id);
       setCustomerId(cId);
       setTitle(title);
       setDescription(description);
       setContact(contact);
       setLocation(location);
       setStart(start);
       setEnd(end);
       
    }

    //Getters and setters for Appointment
    
    public int getAppointmentId() {
        
        return appointmentId.get();
        
    }

    public int getCustomerId() {
        
        return customerId.get();
        
    }

    public String getTitle() {
        
        return title.get();
        
    }
    
    public String getDescription() {
        
        return description.get();
        
    }

    public String getContact() {
        
        return contact.get();
        
    }

    public String getLocation() {
        
        return location.get();
        
    }

    public String getStart() {
        
        return start.get();
        
    }

    public String getEnd() {
        
        return end.get();
        
    }
    
    public void setAppointmentId(int appointmentId) {
        
        this.appointmentId.set(appointmentId);
        
    }
    
    public void setCustomerId(int customerId) {
        
        this.customerId.set(customerId);
        
    }
    
    public void setTitle(String title) {
        
        this.title.set(title);
        
    }
    
    public void setDescription(String description) {
        
        this.description.set(description);
        
    }
    
    public void setContact(String contact) {
        
        this.contact.set(contact);
        
    }
    
    public void setLocation(String location) {
        
        this.location.set(location);
        
    }
    
    public void setStart(String start) {
        
        this.start.set(start);
        
    }
    
    public void setEnd(String end) {
        
        this.end.set(end);
        
    }
    
    //Appointment Start time formatting
    
    public StringProperty getStartProperty() {
        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime ldt = LocalDateTime.parse(this.start.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid); 
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return date;
        
    }
    
    //Appointment End time formatting
    
    public StringProperty getEndProperty() {
        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime ldt = LocalDateTime.parse(this.end.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid); 
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return date;
        
    }
    
    public StringProperty getTitleProperty() {
        
        return this.title;
        
    }
    
    public StringProperty getDescriptionProperty() {
        
        return this.description;
        
    }
    
    public StringProperty getContactProperty() {
        
        return this.contact;
        
    }
    
    public StringProperty getLocationProperty() {
        
        return this.location;
        
    }
    
    //Date based on timezone
    
    public LocalDate getDateOnly() {
        
        Timestamp ts = Timestamp.valueOf(this.start.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalDate ld;
        if(this.location.get().equals("New York")) {
            zid = ZoneId.of("America/New_York");
        } else if(this.location.get().equals("Phoenix")) {
            zid = ZoneId.of("America/Phoenix");
        } else {
            zid = ZoneId.of("Europe/London");
        }
        zdt = ts.toLocalDateTime().atZone(zid);
        ld = zdt.toLocalDate();
        return ld;
        
    }
    
    //Time based on timezone
    
    public String getTimeOnly() {
        
        Timestamp ts = Timestamp.valueOf(this.start.get());
        ZonedDateTime zdt;
        ZoneId zid;
        LocalTime lt;
        if(this.location.get().equals("New York")) {
            zid = ZoneId.of("America/New_York");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().minusHours(4);
        } else if(this.location.get().equals("Phoenix")) {
            zid = ZoneId.of("America/Phoenix");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().minusHours(7);
        } else {
            zid = ZoneId.of("Europe/London");
            zdt = ts.toLocalDateTime().atZone(zid);
            lt = zdt.toLocalTime().plusHours(1);
        }
        int rawH = Integer.parseInt(lt.toString().split(":")[0]);
        if(rawH > 12) {
            rawH -= 12;
        }
        String ampm;
        if(rawH < 9 || rawH == 12) {
            ampm = "PM";
        } else {
            ampm = "AM";
        }
        String time = rawH + ":00 " + ampm;
        return time;
        
    }
    
    public String getTimeFifteen() {
        
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime ldt = LocalDateTime.parse(this.start.getValue(), df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid); 
        DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("kk:mm"); 
	LocalTime localTime = LocalTime.parse(utcDate.toString().substring(11,16), tFormatter);
        return localTime.toString();
        
    }
    
    //List of appointments for current month based on selected customer
    
    public static ObservableList<Appointment> getMonthlyAppointments (int id) {
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND " + 
                "start >= '" + begin + "' AND start <= '" + end + "'"; 
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                appointment = new Appointment(results.getInt("appointmentId"), results.getInt("customerId"), results.getString("start"),
                    results.getString("end"), results.getString("title"), results.getString("description"),
                    results.getString("location"), results.getString("contact"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
        
    }
    
    //List of appointment for current week based on selected customer
    
    public static ObservableList<Appointment> getWeeklyAppoinments(int id) {
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND " + 
                "start >= '" + begin + "' AND start <= '" + end + "'";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                appointment = new Appointment(results.getInt("appointmentId"), results.getInt("customerId"), results.getString("start"),
                    results.getString("end"), results.getString("title"), results.getString("description"),
                    results.getString("location"), results.getString("contact"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            return null;
        }
        
    }
    
    //Alert for appointment within 15 mins of login
    
    public static Appointment appointmentIn15Min() {
        
        Appointment appointment;
        LocalDateTime now = LocalDateTime.now();
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zid);
        LocalDateTime ldt = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime ldt2 = ldt.plusMinutes(15);
        String user = User.currentUser.getUsername();
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start BETWEEN '" + ldt + "' AND '" + ldt2 + "' AND " + 
                "contact='" + user + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                appointment = new Appointment(results.getInt("appointmentId"), results.getInt("customerId"), results.getString("start"),
                    results.getString("end"), results.getString("title"), results.getString("description"),
                    results.getString("location"), results.getString("contact"));
                return appointment;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return null;
        
    }
    
    //Save new appointment to the DB
    
    public static boolean saveAppointment(int id, String type, String contact, String location, String date, String time) {
        
        String title = type.split(":")[0];
        String description = type.split(":")[1];
        String tsStart = createTimeStamp(date, time, location, true);
        String tsEnd = createTimeStamp(date, time, location, false);
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "INSERT INTO appointment SET customerId='" + id + "', userId='" + '6' + "', title='" + title + "', description='" +
                description + "', contact='" + contact + "', location='" + location + "', type='" + type + "', start='" + tsStart + "', end='" + 
                tsEnd + "', url='', createDate=NOW(), createdBy='', lastUpdate=NOW(), lastUpdateBy=''";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return false;
        
    }
    
    //Save changes to selected appointment to the DB
    
    public static boolean updateAppointment(int id, String type, String contact, String location, String date, String time) {
        
        String title = type.split(":")[0];
        String description = type.split(":")[1];
        String tsStart = createTimeStamp(date, time, location, true);
        String tsEnd = createTimeStamp(date, time, location, false);
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "UPDATE appointment SET title='" + title + "', description='" + description + "', contact='" +
                contact + "', location='" + location + "', start='" + tsStart + "', end='" + tsEnd + "' WHERE " +
                "appointmentId='" + id + "'";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return false;
        
    }
    
    //Alert for overlapping appointments
    
    public static boolean overlappingAppointment(int id, String location, String date, String time) {
        
        String start = createTimeStamp(date, time, location, true);
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start = '" + start + "' AND location = '" + location + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                if(results.getInt("appointmentId") == id) {
                    statement.close();
                    return false;
                }
                statement.close();
                return true;
            } else {
                statement.close();
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("SQLExcpection: " + ex.getMessage());
            return true;
        }
        
    }
    
    //Remove selected appointment from the DB
    
    public static boolean deleteAppointment(int id) {
        
        try {
            Statement statement = Database.getDBConnection().createStatement();
            String query = "DELETE FROM appointment WHERE appointmentId = " + id;
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return false;
        
    }
    
    public static String createTimeStamp(String date, String time, String location, boolean startMode) {
        
        String h = time.split(":")[0];
        int rawH = Integer.parseInt(h);
        if(rawH < 9) {
            rawH += 12;
        }
        if(!startMode) {
            rawH += 1;
        }
        String rawD = String.format("%s %02d:%s", date, rawH, "00");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        LocalDateTime ldt = LocalDateTime.parse(rawD, df);
        ZoneId zid;
        if(location.equals("New York")) {
            zid = ZoneId.of("America/New_York");
        } else if(location.equals("Phoenix")) {
            zid = ZoneId.of("America/Phoenix");
        } else {
            zid = ZoneId.of("Europe/London");
        }
        ZonedDateTime zdt = ldt.atZone(zid);
        ZonedDateTime utcDate = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        ldt = utcDate.toLocalDateTime();
        Timestamp ts = Timestamp.valueOf(ldt); 
        return ts.toString();
        
    }
    
}

