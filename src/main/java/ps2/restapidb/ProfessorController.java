package ps2.restapidb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
class ProfessorController {

	@Autowired
	private ProfessorRepo professorRepo;

	public ProfessorController() {

	}

	@GetMapping("/api/professores")
	Iterable<Professor> getProfessores() {
		return professorRepo.findAll();
	}
	
	@GetMapping("/api/professores/{id}")
	Optional<Professor> getProfessor(@PathVariable long id) {
		return professorRepo.findById(id);
	}
	
	@PostMapping("/api/professores")
	Professor createProfessor(@RequestBody Professor p) {
		Professor createdProf = professorRepo.save(p);
		return createdProf;
	}
	
	@PutMapping("/api/professores/{professorId}")
	Optional<Professor> updateProfessor(@RequestBody Professor professorRequest, @PathVariable long professorId) {
		Optional<Professor> opt = this.getProfessor(professorId);
		if (opt.isPresent()) {
			Professor professor = opt.get();
			if (professorRequest.getId() == professor.getId()) {
				professor.setNome(professorRequest.getNome());
				professor.setMatricula(professorRequest.getMatricula());
				professor.setArea(professorRequest.getArea());
				return opt;
			}
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao alterar dados do professor com id " + professorId);	
	}	
	
	@DeleteMapping(value = "/api/professores/{id}")
	void deleteProfessor(@PathVariable long id) {
		professorRepo.deleteById(id);
	}		
}


