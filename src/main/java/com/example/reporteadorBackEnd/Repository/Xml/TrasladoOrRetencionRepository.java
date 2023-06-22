package com.example.reporteadorBackEnd.Repository.Xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoOrRetencionXmlEntity;

public interface TrasladoOrRetencionRepository extends JpaRepository<TrasladoOrRetencionXmlEntity, Long> {
    
    List<TrasladoOrRetencionXmlEntity> findByStatus(Boolean status, Sort sort);
    
    List<TrasladoOrRetencionXmlEntity> findByIdConcepto(ConceptosXmlEntity id);

    @Query(value = "SELECT SUM(t.importe) FROM traslado_or_retencion_xml AS t WHERE t.is_traslado = true", nativeQuery = true)
    List<String> sumaImporteTraslados();

    @Query(value = "SELECT SUM(t.importe) FROM traslado_or_retencion_xml AS t WHERE t.is_retencion = true", nativeQuery = true)
    List<String> sumaImporteRetenidos();

    @Query(value = "SELECT t.id_tasa_cuota, t.id_impuesto, t.id_tipo_factor, t.is_traslado, t.is_retencion, SUM(t.importe), COUNT(*) FROM traslado_or_retencion_xml AS t " 
    + "where t.status = true GROUP BY t.id_tasa_cuota, t.id_impuesto, t.id_tipo_factor, t.is_traslado, t.is_retencion ORDER BY t.id_impuesto", nativeQuery = true)
    List<String> sumaAndgroupByTasaCuota();

    @Query(value="select tc.valor_maximo from tasa_cuota tc inner join traslado_or_retencion_xml tl on tc.id = tl.id_tasa_cuota group by tc.valor_maximo"
    + " ORDER BY tc.valor_maximo DESC", nativeQuery = true)
    List<String> innerJoinTasaCuotaId();
}
