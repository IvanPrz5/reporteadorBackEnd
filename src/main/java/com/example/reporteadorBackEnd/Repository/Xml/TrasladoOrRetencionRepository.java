package com.example.reporteadorBackEnd.Repository.Xml;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoOrRetencionXmlEntity;

public interface TrasladoOrRetencionRepository extends JpaRepository<TrasladoOrRetencionXmlEntity, Long> {
    
    List<TrasladoOrRetencionXmlEntity> findByStatus(Boolean status, Sort sort);
    
    List<TrasladoOrRetencionXmlEntity> findByIdComprobante(ComprobanteXmlEntity idComprobante);

    List<TrasladoOrRetencionXmlEntity> findByIdConcepto(ConceptosXmlEntity id);

    @Query(value = "SELECT SUM(t.importe) FROM traslado_or_retencion_xml AS t WHERE t.id_comprobante_xml = ?1 AND t.status = true AND t.is_traslado = true", nativeQuery = true)
    List<String> sumaImporteTraslados(Long id);

    @Query(value = "SELECT SUM(t.importe) FROM traslado_or_retencion_xml AS t WHERE t.id_comprobante_xml = ?1 AND t.status = true AND t.is_retencion = true", nativeQuery = true)
    List<String> sumaImporteRetenidos(Long id);

    @Query(value = "SELECT t.id_tasa_cuota, t.id_impuesto, t.id_tipo_factor, t.is_traslado, t.is_retencion, SUM(t.importe), SUM(t.base), COUNT(*) FROM traslado_or_retencion_xml AS t" 
    + " WHERE t.id_comprobante_xml = ?1 AND t.status = true GROUP BY t.id_tasa_cuota, t.id_impuesto, t.id_tipo_factor, t.is_traslado, t.is_retencion ORDER BY t.id_impuesto", nativeQuery = true)
    List<String> sumaAndgroupByTasaCuota(Long id);

    @Query(value="SELECT tc.valor_maximo FROM tasa_cuota tc INNER JOIN traslado_or_retencion_xml tl ON tc.id = tl.id_tasa_cuota WHERE tl.id_comprobante_xml = ?1 AND tl.status = true"
    + " GROUP BY tc.valor_maximo ORDER BY tc.valor_maximo DESC", nativeQuery = true)
    List<String> innerJoinTasaCuotaId(Long id);

    @Query(value="SELECT descripcion FROM moneda WHERE id = ?1", nativeQuery = true)
    List<String> getDescripcionFromMoneda(String id);

    @Query(value="SELECT descripcion FROM forma_pago WHERE id = ?1", nativeQuery = true)
    List<String> getDescripcionFromFormaPago(String id);

    @Query(value="SELECT descripcion FROM metodo_pago WHERE id = ?1", nativeQuery = true)
    List<String> getDescripcionFromMetodoPago(String id);
}
