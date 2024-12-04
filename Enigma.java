package etec;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Enigma - a robot by (your name here)
 */
public class Enigma extends Robot
{
	/**
	 * run: Enigma's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setColors(Color.cyan,Color.black,Color.white); // body,gun,radar (COR)

		// Robot main loop
		while(true) {
			
			ahead(224);//  o robo anda para frente
			turnGunRight(90);// o canhão gira para  a direita
			turnRight(40);// o robo gira para  a direita
			turnGunLeft(130); // o canhão gira para  a esquerda
			ahead(24);//  o robo anda para frente
			turnLeft(69);//  o robo vira para a esquerda
		}
}	
	 //esse metodo faz com que o robo mira o canhão com mais rapidez
	 public void mira(double Adv) { 
	 double A=getHeading()+Adv-getGunHeading();//Criei 2 variaveis recebendo funcoes getHeading e getGunHeading essas funcoes faz com que o robo vire a cabeça em graus radianos fazendo diminuir o tempo
		if (!(A > -180 && A <= 180)) { //aqui ele tentara descobrir qual é o lado mais rapido para girar 
		while (A <= -180) { //se o angulo for menos que -180 ele gira pra direita 
			A += 360; // Calculando o grau
		}
		while (A > 180) {//se o angulo for mais que 180 ele gira pra esquerda
			A -= 360;// Calculando o grau
		}
	}
	turnGunRight(A); // gira para a direita recebendo A
}

	//Essa função é muito utilizada, até pelos robôs do diretório samples. Nela você manda por parâmetro o ângulo(valor do tipo double) 
	//a ser deslocado pelo radar, ou pelo canhão, ou até mesmo do seu robô se você querer ir em direção ao inimigo
/*	public double anguloRelativo(double ANG) {
	if (ANG> -180 && ANG<= 180) {
		return ANG;
	}
	double REL = ANG;
	while (REL<= -180) {
		REL += 360;
	}
	while (ANG> 180) {
		REL -= 360;
	}
	return REL;
}*/
	

	//Esse procedimento melhora o tiro do seu robô, você não disperdiçará energia e o robô parará de atirar de muito longe quando a energia 	
	//dele for menor que 15.
	public void fogo(double Distancia) {
		if (Distancia > 200 || getEnergy() < 15) { // Se distancia for maior que 200(pixels) OU getEnergy (um metodo de energia do robo) menor que 15
		fire(1); // ele atira 1 (tiro fraco)
			
	} else if (Distancia > 50) { // se distancia for maior que 50
		fire(2); // ele atira um tiro de 2 (tiro medio)
	} else { // ou ele atira um tiro de 3 (tiro forte)
		fire(3);//tiro de 3 (tiro forte)
    }
}

	//Esse procedimento da um tiro baseado na energia inimiga, ela é muito boa para dar o último quando o adversário estiver com um tiro para
    // morrer(energia < 12), porque seu robô não disperdiçará energia.
	public void tiroFatal(double EnergiaIni) { // Criando um parametro EnergiaIni em um metodo
		double Tiro = (EnergiaIni / 4) + .1; // criamos uma variavel que recebe parametro dividido por 4 somando mais 1 
		fire(Tiro);	//fire (comando para o robo atirar) recebe variavel tiro
}
	
	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	/*	// Replace the next line with any behavior you would like
		//para mirar o radar no adversário.
		turnRadarRight(anguloRelativo(e.getBearing()+getHeading()-getRadarHeading()));
		//para mirar o canhão no adversário.
		turnGunRight(anguloRelativo(e.getBearing()+getHeading()-getGunHeading())); 
		//para virar seu robô em direção do adversário
	    turnRight(anguloRelativo(e.getBearing())); */
		
		fogo(e.getDistance());//chamando o metodo fogo com o parametro e.getDistance que ve a distancia do robo inimigo
		fireBullet(3);// define o dano e a energia gasta no tiro
		//mira o canhão contra o adversário
		mira(e.getBearing());//chamando o metodo fogo com o parametro e.getBearing que Retorna o rolamento para o robô  
		fire(2); //atira 2 (meio forte)
	}
	 public void onHitRobot(HitRobotEvent e) {
	 
	 if(e.getEnergy() < 12) {//se energira menor que 12
	tiroFatal(e.getEnergy()); // o metodo vai receber a energia e dar o tiro (fire(Tiro))
	} else { //se nao 
	fire(2);// da um tiro de 2
	}
	
 }
	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
	ahead(50); // o robo vai pra frente
	
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
	//	back(20);
		turnGunRight(180); //ele gira o canhão para a direita
		turnRight(70);// ele gira o robo para a direita
		ahead(100);// o robo para a frente
	}
}
	

