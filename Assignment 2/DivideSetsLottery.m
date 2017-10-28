function [S1A,S1B,S2A,S2B,L1A,L1B,L2A,L2B]= DivideSetsLottery(CharTable1,CharTable2,Labels1,Labels2,N) 
%Divides CharTable1 and CharTable2 into two sets. Sets 1A and
%2A have together N character samples per each class. The 1-stroke
%characters are included into the former set and the 2-stroke
%characters into the latter one. The rest of the samples are
%included into sets 2A and 2B. Vectors L1A ,L1B, L2A, and L2B
%consist of the class labels. 
%You can use this function for creating a prototype and test set
%from the characters tables loaded from charactersX.mat files.

S1A=zeros(0,size(CharTable1,2),size(CharTable1,3));
S2A=zeros(0,size(CharTable2,2),size(CharTable2,3));
S1B=zeros(0,size(CharTable1,2),size(CharTable1,3));
S2B=zeros(0,size(CharTable2,2),size(CharTable2,3));
L1A=zeros(1,0);
L2A=zeros(1,0);
L1B=zeros(1,0);
L2B=zeros(1,0);

for class=0:1:9
  
  mask1=find(Labels1==class);
  mask2=find(Labels2==class);

  n1=size(mask1,2);
  n2=size(mask2,2);
  ntotal=n1+n2;
  ratio=n1/(ntotal);
  N1=floor(ratio*N);
  N2=N-N1;
  %Selects prototypes for each class from both
  %the 1-stroke and 2-stroke character tables. More protototypes
  %will be selected from that table which has more samples of the
  %class in question.  

  if (N1==0 && n1>0)
    N1 = 1;
  end

  if (N2==0 && n2>0)
    N2 = 1;
  end
  
    
  p1=randperm(n1);
  p2=randperm(n2);
  P1A=mask1(p1(1,1:N1));
  P2A=mask2(p2(1,1:N2));
  P1B=mask1(p1(1,N1+1:n1));
  P2B=mask2(p2(1,N2+1:n2));
  
  s1A=CharTable1(P1A,:,:);
  s1B=CharTable1(P1B,:,:);
  s2A=CharTable2(P2A,:,:);
  s2B=CharTable2(P2B,:,:);
  
  if (size(s1A,1)~=0)
    if (size(S1A,1)==0)
      S1A=s1A;
      L1A=class*ones(1,size(s1A,1));
    else 
      S1A=[S1A;s1A];
      L1A=[L1A,class*ones(1,size(s1A,1))];
    end 
   end
  
  if (size(s2A,1)~=0)
    if (size(S2A,1)==0)
      S2A=s2A;
      L2A=class*ones(1,size(s2A,1));
    else 
      S2A=[S2A;s2A];
      L2A=[L2A,class*ones(1,size(s2A,1))];
    end
  end
  
  if (size(s1B,1)~=0)
    if (size(S1B,1)==0)
      S1B=s1B;
      L1B=class*ones(1,size(s1B,1));
    else 
      S1B=[S1B;s1B];
      L1B=[L1B,class*ones(1,size(s1B,1))];
    end
  end
  
  if (size(s2B,1)~=0)
    if (size(S2B,1)==0)
      S2B=s2B;
      L2B=class*ones(1,size(s2B,1));
    else 
      S2B=[S2B;s2B];
      L2B=[L2B,class*ones(1,size(s2B,1))];
    end
  end
end