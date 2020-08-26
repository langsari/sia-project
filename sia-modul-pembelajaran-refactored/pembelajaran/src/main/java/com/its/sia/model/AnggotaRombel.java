package com.its.sia.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="anggota_rombel")
public class AnggotaRombel {

	@Id
	@Column(name="id_anggota_rombel")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@org.hibernate.annotations.Type(type="pg-uuid")
	private UUID idAnggotaRombel;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Pd.class)
	@JoinColumn(name = "id_pd")
    private Pd pd;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity=Rombel.class)
	@JoinColumn(name = "id_rombel")
    private Rombel rombel;

	public UUID getIdAnggotaRombel() {
		return idAnggotaRombel;
	}

	public void setIdAnggotaRombel(UUID idAnggotaRombel) {
		this.idAnggotaRombel = idAnggotaRombel;
	}

	public Pd getPd() {
		return pd;
	}

	public void setPd(Pd pd) {
		this.pd = pd;
	}

	public Rombel getRombel() {
		return rombel;
	}

	public void setRombel(Rombel rombel) {
		this.rombel = rombel;
	}	
	
}
