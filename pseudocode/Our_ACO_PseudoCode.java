while not all ants have traversed the graph do
		if antsPerStep =\= 0 and antCount < antMax then
			while antActual < antMax do
				if ((antActual + antsPerStep) <= antMax) {
					antActual += antsPerStep;
	                antList.addAnts(antsPerStep);
	            } else if (antActual < antMax) {
	            	antActual += antMax - antList.size();
	            	antList.addAnts(antMax - antList.size());
	            }
			end while
		end if

		for all ants do
			if ant hasFinished then
				remove ant
                if all nodes visited and pathDistance < bestDistance then
					set new bestDistance
					set new bestPath
					if updateMethod == delayed then
						add pheromon on ants path
					end if
				end if
			else
				determine all feasible neighbor states
				stochastically select a feasible neighbor state // TODO: formeln und berechnungen einf�gen
                if updateMethod == stepbystep then
					add pheromon on ants selected edge
				end if
			end if
		end for
		
		evaporate pheromon
		optionally save the graph situation 
	end while
	