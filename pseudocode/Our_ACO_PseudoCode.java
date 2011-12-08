	while not EndCriterium do
		
		if antsPerStep =\= 0 and antCount < antMax then
			while antActual < antMax and newAnts < antsPerStep do ///TODO: fix our code to something like this
				create a new ant
				increase newAnts
			end while
		end if

		for all ants do
			if ant hasFinished then
				remove ant
                if all nodes visited and pathDistance < bestDistance then
					reset the bestDistance
					reset the bestPath
					
					if updateMethod == delayed then
						add pheromon on ants path
					end if
				end if
			else
				determine all feasible neighbor states
				stochastically select a feasible neighbor state
                if updateMethod == stepbystep then
					add pheromon on ants selected edge
				end if
			end if
		end for
		
		evaporate pheromon
		optionally save the graph situation 
	end while
	