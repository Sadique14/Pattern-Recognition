function [d,D] = DtwDistance(X1,X2,d_max)
%Calculates the Dynamic Time Warping distance d between two strokes 
%X1 and X2.
%Boundary conditions: First and last point are matched against
%each other. 
%Continuity conditions: all the data points are
%matched, data points are matched in the same order they have been
%produced, multiple data points can be matched against a single data point. 
%The cost associated with each matching is the squared Euclidean
%distance between the data points.  
%X1 and X2 are N1*n and and N2*n matrixes, respectively, and their
%rows are the data points consisting of coordinate values.
%D is a N1*n difference matrix between X1 and X2 which can be used
%in LVQ learning. 
%The evaluation of the distance is interrupted if its value exceeds
%d_max. 
%Scheme of the dynamic programming algorithm which solves the
%dtw-problem: 
%
%  X2/j| ^ ^ ^ ^
%      | | | | 
%      | | | |
%      | 3.4.5. etc.
%      |
%      | 1.2.------>
%      |___________
%                X1/i 


N1=size(X1,1);
N2=size(X2,1);
d=0;
D=zeros(N1,size(X1,2));
src=zeros(N2,N1);
cum=zeros(N2,N1);
%1. stage
cum(1,1)=sum((X1(1,:)-X2(1,:)).^2);
src(1,1)=4; %beginning 
%2. stage
for i=2:1:N1
  cum(1,i)=cum(1,i-1) + sum((X1(i,:)-X2(1,:)).^2);
  src(1,i)=3;  % --
end
%3. stage
for j=2:1:N2
  cum(j,1)=cum(j-1,1) + sum((X2(j,:)-X1(1,:)).^2);
  src(j,1)=2; % |
end
%stages 4., 5., etc.
for i=2:1:N1
  col_min=0;
  for j=2:1:N2
    inc=sum((X2(j,:)-X1(i,:)).^2);
    src1=cum(j-1,i-1)+inc;
    src2=cum(j-1,i)+inc;
    src3=cum(j,i-1)+inc;
    
    src_min=min([src1 src2 src3]);
    s=find([src1 src2 src3] == src_min);
    cum(j,i)=src_min;
    src(j,i)=s(1);

    
    if (src_min<col_min)
      col_min=inc_min;
    end
  end
  if (col_min>d_max)
    d=realmax;
    break; %evaluation of the distance is interrupted as it cannot
           %be less than d_max
  end
end

if (d~=realmax)
  d=cum(N2,N1);
  i=0;j=0;
  while (s~=4)
    D(N1-i,:)= D(N1-i,:)+X2(N2-j,:)-X1(N1-i,:);
    s=src(N2-j,N1-i);
    if (s==1)
      i=i+1;
      j=j+1;
    end
    if (s==2)
      i=i;
      j=j+1;
    end
    if (s==3)
      i=i+1;
      j=j;
    end
  end
end