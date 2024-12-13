package Client.Components;

import com.example.HospitalInfoSystem.Entities.Appointment;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AppointmentTableModel extends AbstractTableModel {

    private List<Appointment> appointments;
    private final String[] columnNames = {"ID", "Врач", "Пациент", "Дата и время", "Статус"};

    public AppointmentTableModel() {
        this.appointments = List.of();
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        fireTableDataChanged();
    }

    public Appointment getAppointmentAt(int rowIndex) {
        return appointments.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return appointments.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Appointment appointment = appointments.get(rowIndex);
        switch (columnIndex) {
            case 0: return appointment.getAppointmentId();
            case 1: return appointment.getDoctor().getName() + appointment.getDoctor().getSurname();
            case 2: return appointment.getPatient().getName() + appointment.getPatient().getSurname();
            case 3: return appointment.getDateTime();
            case 4: return appointment.getStatus();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
