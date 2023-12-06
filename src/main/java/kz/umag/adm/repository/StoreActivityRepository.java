package kz.umag.adm.repository;

import kz.umag.adm.model.StoreActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreActivityRepository extends JpaRepository<StoreActivity, Integer> {

    Optional<StoreActivity> findByStoreId(int storeId);
}
