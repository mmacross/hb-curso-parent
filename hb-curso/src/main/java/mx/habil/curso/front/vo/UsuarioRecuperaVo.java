package mx.habil.curso.front.vo;

import lombok.Getter;
import lombok.Setter;

public class UsuarioRecuperaVo {
	private @Getter @Setter String id;
	private @Getter @Setter String cveUsuario;
	private @Getter @Setter String nombre;
	private @Getter @Setter String apPaterno;
	private @Getter @Setter String apMaterno;
	private @Getter @Setter Boolean activo;
	private @Getter @Setter String fechaRegistro;
}
