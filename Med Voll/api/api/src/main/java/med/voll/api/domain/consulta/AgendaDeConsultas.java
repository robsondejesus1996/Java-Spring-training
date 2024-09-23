package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
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
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        if(dados.idMedico() != null && !medicoReposiory.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do medico informado não existe!");
        }

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() !=null ){
            return medicoReposiory.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatório quando o médico não for encolhido");
        }

        return medicoReposiory.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }
}
