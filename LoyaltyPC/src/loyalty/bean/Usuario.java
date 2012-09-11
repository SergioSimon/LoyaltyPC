package loyalty.bean;










public class Usuario{
	
	@Override
	public String toString() {
		return "Usuario [Nombre=" + Nombre + ", Apellidos=" + Apellidos
				+ ", DNI=" + DNI + ", Email=" + Email + ", User=" + User
				+ ", Pass=" + Pass + ", Puntos=" + Puntos + ", FechaAlta="
				+ FechaAlta + "]";
	}

	private String Nombre = "";
	private String Apellidos = "";
	private String DNI = "";
	private String Email = "";
	private String User = "";
	private String Pass = "";
	private int Puntos = 0;
	private String FechaAlta = "";
	
	
	
	public Usuario(String nombre, String apellidos, String dNI, String email,
			String user, String pass, int puntos, String fechaAlta) {

		Nombre = nombre;
		Apellidos = apellidos;
		DNI = dNI;
		Email = email;
		User = user;
		Pass = pass;
		Puntos = puntos;
		FechaAlta = fechaAlta;
	}
	


	public String getNombre() {
		return Nombre;
	}
	
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	public String getApellidos() {
		return Apellidos;
	}
	
	public void setApellidos(String apellidos) {
		Apellidos = apellidos;
	}
	
	public String getDNI() {
		return DNI;
	}
	
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	
	public String getEmail() {
		return Email;
	}
	
	public void setEmail(String email) {
		Email = email;
	}
	
	public String getUser() {
		return User;
	}
	
	public void setUser(String user) {
		User = user;
	}
	
	public String getPass() {
		return Pass;
	}
	
	public void setPass(String pass) {
		Pass = pass;
	}
	
	public int getPuntos() {
		return Puntos;
	}
	
	public void setPuntos(int puntos) {
		Puntos = puntos;
	}
	
	public String getFechaAlta() {
		return FechaAlta;
	}
	
	public void setFechaAlta(String fechaAlta) {
		FechaAlta = fechaAlta;
	}
}





