package br.com.lojasrenner.repository;

import br.com.lojasrenner.domain.entity.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<VendaEntity, Long> {
}
