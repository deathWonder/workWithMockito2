import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class MedicalServiceImplTest {

    @ParameterizedTest
    @MethodSource("sourceCheckBloodPressure")
    public void checkBloodPressureTestIf(BloodPressure bloodPressure, int expected) {
        //arrange
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo("99", null, null, null,
                        new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80))));

        SendAlertServiceImpl sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        Mockito.doNothing().when(sendAlertService).send(Mockito.anyString());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        //act
        medicalService.checkBloodPressure("99", bloodPressure);
        //assert
        Mockito.verify(sendAlertService, Mockito.times(expected)).send(Mockito.anyString());

    }

    public static Stream<Arguments> sourceCheckBloodPressure() {
        return Stream.of(
                Arguments.of(new BloodPressure(130, 70), 1),
                Arguments.of(new BloodPressure(120, 80), 0)
        );
    }

    @Test
    public void checkBloodPressureTestString() {
        //arrange
        String expected = "Warning, patient with id: 99, need help";

        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);

        Mockito.when(patientInfoFileRepository.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo("99", null, null, null,
                        new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80))));

        SendAlertServiceImpl sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        Mockito.doNothing().when(sendAlertService).send(Mockito.anyString());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        //act
        medicalService.checkBloodPressure("99", new BloodPressure(130, 70));
        //assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals(expected, argumentCaptor.getValue());
    }


    @ParameterizedTest
    @MethodSource("sourceCheckTemperature")
    public void checkTemperatureTestIf(BigDecimal temperature, int expected) {
        //arrange
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo("99", null, null, null,
                        new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80))));

        SendAlertServiceImpl sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        Mockito.doNothing().when(sendAlertService).send(Mockito.anyString());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        //act
        medicalService.checkTemperature("99", temperature);
        //assert
        Mockito.verify(sendAlertService, Mockito.times(expected)).send(Mockito.anyString());

    }

    public static Stream<Arguments> sourceCheckTemperature() {
        return Stream.of(
                Arguments.of(new BigDecimal("34"), 1),
                Arguments.of(new BigDecimal("36.6"), 0)
        );
    }

    @Test
    public void checkCheckTemperatureTestString() {
        //arrange
        String expected = "Warning, patient with id: 99, need help";

        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);

        Mockito.when(patientInfoFileRepository.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo("99", null, null, null,
                        new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80))));

        SendAlertServiceImpl sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        Mockito.doNothing().when(sendAlertService).send(Mockito.anyString());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        //act
        medicalService.checkTemperature("99", new BigDecimal("34"));
        //assert
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals(expected, argumentCaptor.getValue());
    }
}

