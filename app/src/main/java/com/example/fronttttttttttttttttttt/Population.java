package com.example.fronttttttttttttttttttt;

import java.util.LinkedList;

public class Population {

        private LinkedList<Clan> ListClans = new LinkedList<Clan>();

        public  Population()
        {   }

        public  Population(LinkedList <Clan> ListClans)
        {
            this.ListClans=ListClans;
        }

        public LinkedList<Clan> getListClans()
        {
            return ListClans;
        }

        public void setListClans(LinkedList<Clan> listClans)
        {
            ListClans = listClans;
        }

        public Individu bestC(Object M[][]){
            Individu meilleur =ListClans.get(0).bestP(M);
            for(Clan C:ListClans)
            {

                if(C.bestP(M).coutIndiv< meilleur.coutIndiv)
                {
                    meilleur=C.bestP(M);
                }

            }
            return meilleur;
        }


        public Individu randomPop()
        {
            return ListClans.get((int)(Math.random()*(getListClans().size()-1))).randomP();

        }

        public PC searchC (Individu a)
        {
            for(Clan C:ListClans)
            {
                for(Pod P: C.getListPods())
                {
                    for(Individu I: P.getListIndividus())
                    {
                        if(I.getId()==a.getId()) {
                            return new PC(P,C);
                        }
                    }
                }

            }
            return null;
        }



        public void addClan(Clan c) {
            this.ListClans.add(c);
        }


}
