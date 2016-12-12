package jus.poc.prodcons.v2;

public class monSemaphore {

	public int place;
	public int attente;
	
	public monSemaphore(int val) {
		// TODO Auto-generated constructor stub
		place = val;
		attente = 0;
	}
	
	//prise du s�maphore
	public synchronized void P(){
		//Le verrou peut �tre acquis il reste de la place
		if(place > 0)
			place--;
		//sinon on ajoute un element en attente et on wait une place
		else{
			attente++;
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//liberation du semaphore
	public synchronized void V(){
		//si du monde attend alors on les lib�re et on leur permet d'entrer
		if(attente > 0){
			notify();
			attente--;
		}
		//sinon on ajoute une place dans le semaphore
		else
			place++;
	}

}
