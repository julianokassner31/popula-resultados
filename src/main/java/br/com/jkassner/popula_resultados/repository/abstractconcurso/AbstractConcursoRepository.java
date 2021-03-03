package br.com.jkassner.popula_resultados.repository.abstractconcurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/*
 * @created 08/11/2020 - 17:45
 * @project api-loteria
 * @author Juliano Kassner
 */
@NoRepositoryBean
public interface AbstractConcursoRepository<T> extends JpaRepository<T, Long> {
    T findFirstByOrderByDtSorteioDesc();
}
