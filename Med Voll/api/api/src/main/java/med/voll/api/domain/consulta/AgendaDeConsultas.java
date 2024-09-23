package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.MedicoReposiory;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoReposiory medicoReposiory;

    @Autowired
    private PacienteRepository pacienteRepository;
    public void agendar(DadosAgendamentoConsulta dados){

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = medicoReposiory.findById(dados.idMedico()).get();

        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);

    }
}
