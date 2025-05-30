package FitStore.TPO_Grupo10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import FitStore.TPO_Grupo10.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Este repositorio hereda de JpaRepository, lo que le proporciona métodos CRUD básicos.
    // No es necesario agregar métodos adicionales a menos que se necesiten consultas personalizadas.

    //Nos trae un Crud completo para la entidad Producto
    // Puedes agregar métodos personalizados aquí si es necesario, por ejemplo:
}