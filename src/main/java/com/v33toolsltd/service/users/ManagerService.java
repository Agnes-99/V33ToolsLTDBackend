package com.v33toolsltd.service.users;


import com.v33toolsltd.domain.users.Manager;
import com.v33toolsltd.repository.users.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService implements IManagerService {

    private final ManagerRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerService(ManagerRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Manager create(Manager manager) {
        manager = new Manager.Builder()
                .copy(manager)
                .setPassword(passwordEncoder.encode(manager.getPassword()))
                .build();
        return repository.save(manager);
    }

    @Override
    public Manager read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Manager update(Manager manager) {
        return repository.save(manager);
    }

    @Override
    public boolean delete(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Manager> getAll() {
        return repository.findAll();
    }
}
