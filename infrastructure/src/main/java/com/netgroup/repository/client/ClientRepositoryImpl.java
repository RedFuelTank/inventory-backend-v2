package com.netgroup.repository.client;

import com.netgroup.entity.client.Business;
import com.netgroup.entity.client.Representative;
import com.netgroup.repository.client.model.BusinessModel;
import com.netgroup.repository.client.model.RepresentativeModel;
import com.netgroup.usecase.client.api.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {
    @PersistenceContext
    private EntityManager manager;

    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public Representative register(Representative representative) {
        if (doesRepresentativeExist(representative.getUsername())) {
            throw new IllegalArgumentException();
        }

        RepresentativeModel model = RepresentativeModel.builder()
                .username(representative.getUsername())
                .enabled(true)
                .password(passwordEncoder.encode(representative.getPassword()))
                .authority(representative.getAuthority())
                .build();

        manager.persist(model);

        return representative;
    }

    @Override
    @Transactional
    public Business registerBusiness(Business business) {
        if (doesBusinessExist(business.getName())) {
            throw new IllegalArgumentException();
        }
        Representative representative = business.getRepresentative();

        RepresentativeModel representativeModel = RepresentativeModel.builder()
                .username(representative.getUsername())
                .password(representative.getPassword())
                .enabled(true)
                .authority(representative.getAuthority())
                .build();

        BusinessModel model = BusinessModel.builder()
                .name(business.getName())
                .password(passwordEncoder.encode(business.getPassword()))
                .authority(business.getAuthority())
                .enabled(true)
                .representative(representativeModel)
                .build();

        manager.persist(model);

        return business;
    }

    @Override
    public Optional<Representative> getRepresentativeByUsername(String representativeUsername) {
        TypedQuery<RepresentativeModel> query = manager.createQuery("select r from RepresentativeModel r where username = :username", RepresentativeModel.class);
        query.setParameter("username", representativeUsername);

        Optional<RepresentativeModel> possibleRepresentativeModel = query.getResultStream().findFirst();

        if (possibleRepresentativeModel.isPresent()) {
            RepresentativeModel representativeModel = possibleRepresentativeModel.get();
            return Optional.of(Representative.builder()
                    .username(representativeModel.getUsername())
                    .password(representativeModel.getPassword())
                    .build());
        } else {
            return Optional.empty();
        }
    }

    private boolean doesRepresentativeExist(String username) {
        TypedQuery<RepresentativeModel> query = getRepresentativeQueryWith(username);

        return query.getResultStream().findFirst().isPresent();
    }

    private TypedQuery<RepresentativeModel> getRepresentativeQueryWith(String username) {
        TypedQuery<RepresentativeModel> query = manager.createQuery("select r from RepresentativeModel r where username = :username", RepresentativeModel.class);
        query.setParameter("username", username);
        return query;
    }

    private boolean doesBusinessExist(String name) {
        TypedQuery<BusinessModel> query = getBusinessModelTypedQueryWith(name);

        return query.getResultStream().findFirst().isPresent();
    }

    private TypedQuery<BusinessModel> getBusinessModelTypedQueryWith(String name) {
        TypedQuery<BusinessModel> query = manager.createQuery("select b from BusinessModel b where name = :name", BusinessModel.class);
        query.setParameter("name", name);
        return query;
    }
}
