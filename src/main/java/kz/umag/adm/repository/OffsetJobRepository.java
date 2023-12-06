package kz.umag.adm.repository;

import kz.umag.adm.model.OffsetJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OffsetJobRepository extends JpaRepository<OffsetJob, Integer> {

    Optional<OffsetJob> findByName(String name);
}
