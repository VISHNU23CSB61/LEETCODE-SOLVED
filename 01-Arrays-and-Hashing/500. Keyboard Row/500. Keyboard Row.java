public String[] findWords(String[] words) {
        if(words == null || words.length == 0) return new String[0];
        HashSet<Character>[] keyboard = new HashSet[3];
        keyboard[0] = new HashSet<Character>(Arrays.asList('q','w','e','r','t','y','u','i','o','p','Q','W','E','R','T','Y','U','I','O','P'));
        keyboard[1] = new HashSet<Character>(Arrays.asList('a','s','d','f','g','h','j','k','l','A','S','D','F','G','H','J','K','L'));
        keyboard[2] = new HashSet<Character>(Arrays.asList('z','x','c','v','b','n','m','Z','X','C','V','B','N','M'));
        
        List<String> ret = new ArrayList<String>();
        for(String w : words){
            int idx = -1;
            for(int i = 0; i < 3; i ++){
                if(keyboard[i].contains(w.charAt(0))){
                    idx = i;
                    break;
                }
            }
            int i = 1;
            for(; i < w.length(); i ++){
                if(!keyboard[idx].contains(w.charAt(i))) break;
            }
            if(i == w.length()) ret.add(w);
        }
        
        String[] res = new String[ret.size()];
        for(int i = 0; i < res.length; i ++){
            res[i] = ret.get(i);
        }
        return res;
    }