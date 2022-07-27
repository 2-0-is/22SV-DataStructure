// In-memory adaptation of Linear Hashing for Strings.
//
// Bongki Moon (bkmoon@snu.ac.kr), Mar/27/2017

import java.util.*;

public class LinearHash {

  private int HTSize;
  private long splitIndex;
  Hashtable<Long, Vector<String>> hashtable;

  // constructor for the Hash table
  public LinearHash(int HTinitSize)	{
    this.HTSize = HTinitSize;
    hashtable = new Hashtable<>();
    splitIndex = 0;
  }

  // insert `word' to the Hash table.
  public int insertUnique(String word) {
    if(lookup(word)>0) return -1;

    long hashValue = MyUtil.ELFhash(word, HTSize);

    //hashvalue가 split 되었으면 2*HTSize로 key 결정
    if(hashValue<splitIndex) {
      hashValue = MyUtil.ELFhash(word, HTSize*2);
    }

    //split 안되었으면 collision 체크
    //collision 발생시 -> split 되지 않은 처음 키 key에 든 모든 value의 key를 다시 결정 (ELFhash(word, HTSize*2) 사용)
    //split 기록에 체크
    if(!hashtable.get(hashValue).isEmpty()){
      //collision이 일어난 경우
      hashtable.get(hashValue).add(word);
      Vector<String> newWords = hashtable.get(splitIndex);
      for(String s:hashtable.get(splitIndex)){
        long key = MyUtil.ELFhash(s, HTSize*2);
        if(key!=splitIndex){
          hashtable.get(key).add(s);
          newWords.remove(s);
        }
      }
      hashtable.put(splitIndex, newWords);
      splitIndex++;
      if(hashValue<splitIndex) {
        hashValue = MyUtil.ELFhash(word, HTSize*2);
      }
    }
    //collision 발생하지 않은 경우 -> HTSize로 key 결정
    hashtable.get(hashValue).add(word);

    //0~2^k-1 모든 hashKey가 split 되었다면 HTSize 2배로 증가 && split 기록 초기화
    if(splitIndex==HTSize){
      HTSize = HTSize*2;
      splitIndex = 0;
    }

    return (int) hashValue;
  }

  public int lookup(String word) {
    long hashValue = MyUtil.ELFhash(word, HTSize);

    if(hashValue<splitIndex){
      hashValue = MyUtil.ELFhash(word, HTSize*2);
    }

    if(hashtable.get(hashValue).contains(word)) return hashtable.get(hashValue).size();
    else return -1;
  }     // look up `word' in the Hash table.

  public int wordCount() {
    int wordNum = 0;
    for(long i=0;i<HTSize+splitIndex;i++){
      wordNum = wordNum + hashtable.get(i).size();
    }
    return wordNum;
  }
  public int emptyCount() {
    int emptyCount = 0;
    for(long i=0;i<HTSize+splitIndex;i++){
      if(hashtable.get(i).size()==0) emptyCount++;
    }
    return emptyCount;
  }
  public int size() {
    return (int) (HTSize+splitIndex);
  }			// 2^k + collisions in the current round

  public void print() {
    for(long i=0;i<HTSize+splitIndex;i++){
      if(hashtable.get(i).size()==0) continue;
      System.out.print("[" + i + ":");
      for(String s:hashtable.get(i)){
        System.out.print(" " + s);
      }
      System.out.print("]\n");
    }
  }
}
