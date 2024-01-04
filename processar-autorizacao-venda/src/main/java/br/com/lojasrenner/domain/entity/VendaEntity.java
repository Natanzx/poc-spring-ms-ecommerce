package br.com.lojasrenner.domain.entity;

import br.com.lojasrenner.domain.type.SituacaoType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "ECOMMERCE", name = "VENDA")
public class VendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_seq")
    @SequenceGenerator(name = "venda_seq", sequenceName = "ECOMMERCE.VENDA_SEQ", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "CANAL", nullable = false)
    private String canal;

    @Column(name = "CODIGO_EMPRESA", nullable = false)
    private Integer codigoEmpresa;

    @Column(name = "CODIGO_LOJA", nullable = false)
    private Integer codigoLoja;

    @Column(name = "NUMERO_PDV", nullable = false)
    private Integer numeroPdv;

    @Column(name = "NUMERO_PEDIDO", nullable = false)
    private String numeroPedido;

    @Column(name = "NUMERO_ORDEM_EXTERNO", nullable = false)
    private String numeroOrdemExterno;

    @Column(name = "VALOR_TOTAL", nullable = false)
    private Double valorTotal;

    @Column(name = "QTD_ITEM", nullable = false)
    private Integer qtdItem;

    @Column(name = "VENDA_REQUEST", nullable = false)
    private String vendaRequest;

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(name = "DATA_REQUISICAO", nullable = false)
    private LocalDateTime dataRequisicao;

    @Column(name = "CHAVE_NFE")
    private String chaveNfe;

    @Column(name = "NUMERO_NOTA")
    private Long numeroNota;

    @Column(name = "DATA_EMISSAO")
    private LocalDateTime dataEmissao;

    @Column(name = "PDF")
    private String pdf;

    @Column(name = "SITUACAO", nullable = false)
    @Enumerated(EnumType.STRING)
    private SituacaoType situacao;

    @Column(name = "MOTIVO")
    private String motivo;
}
